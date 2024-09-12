export function SignUp() {
  return (
    <>
      <h1>SignUp</h1>
      <div>
        <label htmlFor="username">Kullanıcı Adı</label>
        <input id="username" type="text" />
      </div>
      <div>
        <label htmlFor="email">E-Posta</label>
        <input id="email" type="text" />
      </div>
      <div>
        <label htmlFor="password">Şifre</label>
        <input id="password" type="password" />
      </div>
      <div>
        <label htmlFor="passwordRepat">Şifre Tekrarı</label>
        <input id="passwordRepeat" type="password" />
      </div>
    </>
  );
}