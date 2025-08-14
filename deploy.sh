#!/bin/bash

# Exit on any error
set -e

# Configuration
PROJECT_ID="telusrecruitai-468907"
REGION="asia-south1"
SERVICE_NAME="interview-hub"
IMAGE_NAME="interview-hub"

# Function for logging
log() {
    echo "[$(date +'%Y-%m-%d %H:%M:%S')] $1"
}

# Function for error handling
handle_error() {
    log "Error occurred in script at line $1"
    exit 1
}

# Set up error handling
trap 'handle_error $LINENO' ERR

log "Starting deployment process..."

# Build the Docker image
log "Building Docker image..."
docker build -t gcr.io/$PROJECT_ID/$IMAGE_NAME:latest .

# Push the image to Container Registry
log "Pushing image to Container Registry..."
docker push gcr.io/$PROJECT_ID/$IMAGE_NAME:latest

# Deploy to Cloud Run
log "Deploying to Cloud Run..."
gcloud run deploy $SERVICE_NAME \
  --image gcr.io/$PROJECT_ID/$IMAGE_NAME:latest \
  --platform managed \
  --region $REGION \
  --project $PROJECT_ID \
  --allow-unauthenticated

log "Deployment completed successfully!"
