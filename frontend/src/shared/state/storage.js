export function storeToken(token) {
  if (token) {
    localStorage.setItem("token", JSON.stringify(token));
  } else {
    localStorage.removeItem("token");
  }
}

export function loadToken() {
  const tokenInString = localStorage.getItem("token");
  if (!tokenInString) return null;
  try {
    return JSON.parse(tokenInString);
  } catch {
    return null;
  }
}