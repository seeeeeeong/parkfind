#!/bin/bash

IS_BLUE=$(docker ps | grep parkfind-blue)
DEFAULT_CONF=" /etc/nginx/nginx.conf"
MAX_RETRIES=20

check_service() {
  local RETRIES=0
  local URL=$1
  while [ $RETRIES -lt $MAX_RETRIES ]; do
    echo "Checking service at $URL... (attempt: $((RETRIES+1)))"
    sleep 3

    REQUEST=$(curl $URL)
    if [ -n "$REQUEST" ]; then
      echo "health check success"
      return 0
    fi

    RETRIES=$((RETRIES+1))
  done;

  echo "Failed to check service after $MAX_RETRIES attempts."
  return 1
}

if [ -z "$IS_BLUE" ];then
  echo "### Green => Blue ###"

  echo "1. Blue 이미지 받기"
  docker-compose pull park-blue

  echo "2. Blue 컨테이너 실행"
  docker-compose up -d park-blue

  echo "3. health check"
  if ! check_service "http://3.34.6.245:8080"; then
    echo "Blue health check 가 실패했습니다."
    exit 1
  fi

  echo "4. nginx 재실행"
  sudo cp /etc/nginx/nginx.blue.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo "5. Green 컨테이너 내리기"
  docker-compose stop park-green
  docker-compose rm -f park-green

else
  echo "### Blue => Green ###"

  echo "1. Green 이미지 받기"
  docker-compose pull park-green

  echo "2. Green 컨테이너 실행"
  docker-compose up -d park-green

  echo "3. health check"
  if ! check_service "http://3.34.6.245:8081"; then
      echo "Green health check 가 실패했습니다."
      exit 1
    fi

  echo "4. nginx 재실행"
  sudo cp /etc/nginx/nginx.green.conf /etc/nginx/nginx.conf
  sudo nginx -s reload

  echo "5. Blue 컨테이너 내리기"
  docker-compose stop park-blue
  docker-compose rm -f park-blue
fi