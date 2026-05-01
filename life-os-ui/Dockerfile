# ── Stage 1: Build Angular App ────────────────────────────────────────────────
FROM node:20-alpine AS builder

WORKDIR /app

# Copy package files and install dependencies first (layer caching)
COPY package*.json ./
RUN npm ci --legacy-peer-deps

# Copy the rest of the source code and build for production
COPY . .
RUN npm run build -- --configuration=production

# ── Stage 2: Serve with Nginx ─────────────────────────────────────────────────
FROM nginx:alpine

# Remove default nginx config and replace with ours
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Copy Angular build output into nginx web root
COPY --from=builder /app/dist/life-os-ui/browser /usr/share/nginx/html

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
