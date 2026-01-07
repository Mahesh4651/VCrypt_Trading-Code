import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import tailwindcss from '@tailwindcss/vite'

// https://vite.dev/config/
export default defineConfig({
  define: {
    global: "window",
  },
  plugins: [
    tailwindcss(),
    react()
  ],
  server: {
    port: 80,         // <-- frontend runs on 3000
    strictPort: true,   // fails if 3000 is already in use
    host: true          // allows access from EC2 public IP
  }
})
