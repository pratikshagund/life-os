/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#6366F1',
          dark: '#818CF8',
        },
        background: {
          light: '#F8FAFC',
          dark: '#0B0F19'
        },
        surface: {
          light: '#FFFFFF',
          dark: '#111827'
        }
      },
      fontFamily: {
        sans: ['Inter', 'sans-serif'],
        heading: ['Outfit', 'sans-serif']
      }
    },
  },
  plugins: [],
}
