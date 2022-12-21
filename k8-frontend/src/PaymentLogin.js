import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { PostReq } from "./PostReq";

const PaymentLogin = () => {
  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { token } = useParams();

  const handleSubmit = (e) => {
    e.preventDefault();
    const obj = { mail, password, token };
    PostReq("http://mkb.express.edlcn/api/v1/users/paymentLogin", obj).then((data) => {
      if (data.returnObject === null) {
        alert("Login failed.\n" + data.status);
      } else {
        
        window.localStorage.setItem("isLoggedIn", true);
        window.localStorage.setItem("user", JSON.stringify(data.returnObject));
        navigate("/merchantPayment/" + token);
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

export { PaymentLogin };
