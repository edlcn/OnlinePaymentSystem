import { useState } from "react";
import { PostReq, PostReqAuth } from "./PostReq";
import { Navigate, useNavigate } from "react-router-dom";
import { validateMail, validatePassword } from "./FormValidation";

const Register = () => {
  const [name, setName] = useState("");

  const [mail, setMail] = useState("");
  const [password, setPassword] = useState("");
  const [gender, setGender] = useState("female");
  const navigate = useNavigate();

  const handleRegister = () => {
    const toBeSend = { name, mail, password, gender };
    if (!validateMail(mail)) alert("Invalid Mail Adress.");
    else if (!validatePassword(password)) alert("Invalid Password.");
    else {
      PostReq("https://tfb308.herokuapp.com/api/v1/user/signup", toBeSend).then(
        (data) => {
          if (data.status == "200") {
            navigate("/login");
            alert("Successfully signed up.");
          } else {
            alert("Sign up failed.\n" + data.status);
          }
        }
      );
    }
  };

  return (
    <div className="formf">
      <h2>Register</h2>
      <form>
        <label>Name</label>
        <input
          type="text"
          required
          value={name}
          onChange={(event) => setName(event.target.value)}
        />

        <label>Mail</label>
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
        <label>Gender</label>
        <select value={gender} onChange={(e) => setGender(e.target.value)}>
          <option value="female">Female</option>
          <option value="male">Male</option>
        </select>
      </form>

      <button onClick={handleRegister}>register</button>
    </div>
  );
};

export default Register;
