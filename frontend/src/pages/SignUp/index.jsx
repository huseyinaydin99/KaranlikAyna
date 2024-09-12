import React, { useState } from 'react';

export function SignUp() {
    const [username, setUsername] = useState();
    const [email, setEmail] = useState();
    const [password, setPassword] = useState();
    const [passwordRepeat, setPasswordRepeat] = useState();
    
  return (
    <>
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
      <button disabled={!password && password !== passwordRepeat}>Kaydol</button>
    </>
  );
}