/** @type {import('tailwindcss').Config} */
export default {
	content: [
    './src/**/*.{html,js,jsx,svelte,tsx}'
  ],
	theme: {
		extend: {
			colors: {
				blue1: '#cce4f6',     // 연한 하늘색
				blue2: '#88c9f9',     // 하늘색
				blue3: '#4fa3e3',     // 진한 하늘색
				blue4: '#3182ce',     // 중간 파랑
				blue5: '#2563eb',     // 진한 파랑
				blue6: '#1e40af',     // 네이비 블루
				blue_background: '#f0f8ff', // 하늘 배경색
				sky1: '#bae6fd',
				sky2: '#7dd3fc',
				sky3: '#38bdf8',
				sky4: '#0ea5e9',
				sky5: '#0284c7'
			}
		}
	},
	plugins: [require('daisyui')],
	daisyui: {
		themes: [
			{
				mytheme: {
					primary: '#38bdf8',            // sky3
					'primary-focus': '#0284c7',    // sky5
					'primary-content': '#ffffff'
				}
			}
		]
	}
};