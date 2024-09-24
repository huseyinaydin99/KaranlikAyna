//import axios from "axios";
import http from "../../lib/http";
import { i18nInstance } from "../../locales/index";

export function signUp(body){
    return http.post('/api/v1/users', body/*, {
        headers: {
            "Accept-Language": i18nInstance.language
        }
    }*/);
}