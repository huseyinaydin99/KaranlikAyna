import React, { useState } from 'react';
import axios from "axios";

export function SignUp() {
    const [username, setUsername] = useState();
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [passwordRepeat, setPasswordRepeat] = useState();
    const onSubmit = (event) => {
      event.preventDefault(); //formdan gelen event'i alır ve tarayıcının işlmesini önler böylelikle form submit olunca tarayıcadaki sayfa yenilenmez.!
      console.log(username + 
        email + 
        password);
      axios.post('http://localhost:8080/api/v1/users',{
        username,
        email,
        password
    });
    }
  return (
    <form onSubmit={onSubmit}>
      <h1>Kaydol</h1>
      <div>
        <label htmlFor="username">Kullanıcı Adı</label>
        <input id="username" type="text" onChange={(event) => {setUsername(event.target.value);}} />
      </div>
      <div>
        <label htmlFor="email">E-Posta</label>
        <input id="email" type="text" onChange={(event) => {setEmail(event.target.value);}} />
      </div>
      <div>
        <label htmlFor="password">Şifre</label>
        <input id="password" type="password" onChange={(event) => {setPassword(event.target.value);}} />
      </div>
      <div>
        <label htmlFor="passwordRepeat">Şifre Tekrarı</label>
        <input id="passwordRepeat" type="password" onChange={(event) => {setPasswordRepeat(event.target.value);}} />
      </div>
      <button disabled={!password || password !== passwordRepeat}>Kaydol</button>
    </form>
  );
}