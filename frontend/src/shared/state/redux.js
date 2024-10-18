import { configureStore, createSlice } from "@reduxjs/toolkit";
import { loadAuthState, storeAuthState } from "./storage";

const authSlice = createSlice({
    name: 'auth',
    initialState: loadAuthState(),
    reducers: {
        loginSuccess: (state, action) => {
            state.id = action.payload.id;
            state.username = action.payload.username;
            state.email = action.payload.email;
            state.image = action.payload.image;
        },
        logoutSuccess: (state, action) => {
            state.id = 0;
            delete state.username;
            delete state.email;
            delete state.image;
        }
    }
})
export const { loginSuccess, logoutSuccess } = authSlice.actions;

export const store = configureStore({
    reducer: {
        auth: authSlice.reducer
    }
})

store.subscribe(() => {
    storeAuthState(store.getState().auth)
})

/*
Bu kod, Redux Toolkit kullanarak bir auth slice'ı (dilim) oluşturuyor ve kullanıcı oturum yönetimi (login/logout) işlemlerini yönetiyor.

createSlice ile auth adında bir dilim oluşturulmuş ve oturum açma (loginSuccess) ile oturumu kapatma (logoutSuccess) işlemlerini yapan iki reducer tanımlanmış.
Başlangıç durumu (initialState), yerel depolamadan (local storage) yükleniyor (loadAuthState).
configureStore ile Redux store oluşturuluyor ve auth slice'ı bu store'a ekleniyor.
store.subscribe ile store'daki her güncellemede, mevcut auth durumu yerel depolamaya kaydediliyor (storeAuthState).
*/

/*
Yani istediğimiz alt veya üst komponentden drill yapmadan redux storeden useSelector ile kimlik bilgileri çekilebilir
*/