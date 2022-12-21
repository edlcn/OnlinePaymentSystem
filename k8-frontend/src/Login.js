import { useState } from "react";
import { PostReq, PostReqAuth } from "./PostReq";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = (e) => {
    e.preventDefault();
    const obj = { mail, password };
    PostReq("https://tfb308.herokuapp.com/api/v1/user/login", obj).then((res) => {
      if (res.returnObject === null) {
        alert("Login failed.\n" + res.status);
      } else {
        window.localStorage.setItem("isLoggedIn", true);
        window.localStorage.setItem("user", JSON.stringify(res.returnObject));
        navigate("/payment");
        alert("Login Successful.");
      }
    });
  };

  return (
    <div className="formf">
      <h2>Login</h2>
      <form>
        <label>Mail Adress</label>
        <input
          type="text"
          required
          value={mail}
          onChange={(event) => setMail(event.target.value)}
        />
        <label>Password</label>
        <input
          type="text"
          required
          value={password}
          onChange={(event) => setPassword(event.target.value)}
        />
        <button onClick={handleSubmit}>Login</button>
      </form>
    </div>
  );
};

export default Login;
