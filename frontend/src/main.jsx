import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
//import App from './App.jsx'
import './index.css'
import { SignUp } from './pages/SignUp/index.jsx'
import "./styles.scss"
import i18n from "i18next";
import { initReactI18next } from "react-i18next";

i18n
  .use(initReactI18next)
  .init({
    resources: {
      en: {
        translation: {
          signUp: 'Sign Up',
          username: 'Username',
          email: 'E-mail',
          password: 'Password',
          passwordRepeat: 'Password Repeat',
          passwordMismatch: 'Password mismatch',
          genericError: 'Unexpected error occured. Please try again.'
        }
      },
      tr: {
        translation: {
          signUp: 'Kayit Ol',
          username: 'Kullanıcı Adı',
          email: 'E-posta',
          password: 'Şifre',
          passwordRepeat: 'Şifre Tekrarı',
          passwordMismatch: 'Şifreniz eşleŞmiyor.',
          genericError: 'Tövbeler olsun beklenmedik bir hata oluştu kardeşim. Lütfen tekrar deneyin.'
        }
      }
    },
    fallbackLng: "tr",
    interpolation: {
      escapeValue: false
    }});

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <SignUp />
  </StrictMode>,
);