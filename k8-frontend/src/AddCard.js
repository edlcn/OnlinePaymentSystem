import { useState } from "react";
import { PostReq, PostReqAuth } from "./PostReq";
import { Navigate, useNavigate } from "react-router-dom";
import { validateCreditCard, validateCvv } from "./FormValidation";

const AddCard = () => {
  const [number, setNumber] = useState("");
  const [date, setDate] = useState("");
  const [name, setName] = useState("");
  const [cvv, setCvv] = useState();
  const navigate = useNavigate();
  const userId = JSON.parse(window.localStorage.getItem("user")).id;

  const handleSubmit = () => {
    if (!validateCreditCard(number)) {
      alert("Invalid Credit Card Number.");
      return;
    } else if (!validateCvv(cvv.toString())) {
      alert("Invalid Cvv.");
      return;
    }
    const creditCard = { number, name, date, cvv };
    const toBeSent = { creditCard, userId };
    PostReqAuth("http://mkb.express.edlcn/api/v1/cards", toBeSent)
      .then((res) => res.json())
      .then((data) => {
        if (data.status == "200") {
          alert("Card successfully added.");
          navigate("/myCards");
        } else if (data.status == "400:TOKEN EXPIRED!") {
          window.localStorage.removeItem("isLoggedIn");
          window.localStorage.removeItem("user");
          navigate("/login");
          alert("Token expired, please login again.");
        } else {
          alert("Card could not be added. \n" + data.status);
        }
      });
  };

  return (
    <div className="formf">
      <h2>Add Card</h2>

      <form onSubmit={handleSubmit}>
        <label>Credit Card Number</label>
        <input
          type="text"
          required
          value={number}
          onChange={(event) => setNumber(event.target.value)}
        />
        <label>Name</label>
        <input
          type="text"
          required
          value={name}
          onChange={(event) => setName(event.target.value)}
        />
        <label>Date</label>
        <input
          type="text"
          required
          value={date}
          onChange={(event) => setDate(event.target.value)}
        />
        <label>CVA</label>
        <input
          type="text"
          required
          value={cvv}
          onChange={(event) => setCvv(event.target.value)}
        />
      </form>
      <button onClick={handleSubmit}>ADD</button>
    </div>
  );
};

export default AddCard;
