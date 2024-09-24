import axios from "axios";
import { i18nInstance } from "../locales";

const http = axios.create();

http.interceptors.request.use((config) => {
    config.headers["Accept-Language"] = i18nInstance.language //atılan isteği atılma esnasında yakalayıp ona header ekliyor. Tüm istekler Accept-Language=tr veya en olarak gönderiliyor.
    return config;
})

export default http;