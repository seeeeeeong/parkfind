#!/bin/bash

IS_GREEN=$(docker ps | grep parkfind-green) # 현재 실행중인 App이 green인지 확인합니다.
DEFAULT_CONF="/etc/nginx/nginx.conf"

if [ -z "$IS_GREEN" ]; then # green이 실행 중이지 않다면
  echo "### BLUE => GREEN ###"

  echo "1. get green image"
  docker-compose pull parkfind-green # green 이미지를 내려받습니다.

  echo "2. green container up"
  docker-compose up -d parkfind-green # green 컨테이너 실행

  # green 컨테이너 상태 확인
  while true; do
    echo "Checking if green container is running..."
    GREEN_STATUS=$(docker ps | grep parkfind-green)
    if [ -n "$GREEN_STATUS" ]; then
      echo "Green container is running. Proceeding with health check..."
      break
    else
      echo "Green container is not running yet. Waiting..."
      sleep 3
    fi
  done

  while true; do
    echo "3. green health check..."
    sleep 3
    REQUEST=$(curl -s http://3.37.167.45:8082) # green으로 request
    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
      echo "health check success"
      break
    fi
  done

  echo "4. reload nginx"
  sudo cp /nginx/nginx.green.conf $DEFAULT_CONF
  sudo nginx -s reload

  echo "5. blue container down"
  docker-compose stop parkfind-blue
else
  echo "### GREEN => BLUE ###"

  echo "1. get blue image"
  docker-compose pull parkfind-blue

  echo "2. blue container up"
  docker-compose up -d parkfind-blue

  # blue 컨테이너 상태 확인
  while true; do
    echo "Checking if blue container is running..."
    BLUE_STATUS=$(docker ps | grep parkfind-blue)
    if [ -n "$BLUE_STATUS" ]; then
      echo "Blue container is running. Proceeding with health check..."
      break
    else
      echo "Blue container is not running yet. Waiting..."
      sleep 3
    fi
  done

  while true; do
    echo "3. blue health check..."
    sleep 3
    REQUEST=$(curl -s http://3.37.167.45:8080) # blue로 request

    if [ -n "$REQUEST" ]; then # 서비스 가능하면 health check 중지
      echo "health check success"
      break
    fi
  done

  echo "4. reload nginx"
  sudo cp /nginx/nginx.blue.conf $DEFAULT_CONF
  sudo nginx -s reload

  echo "5. green container down"
  docker-compose stop parkfind-green
fi
