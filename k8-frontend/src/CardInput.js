import { useState } from "react";
import { PostReq, PostReqAuth } from "./PostReq";

const CardInput = () => {
  function randomCost(min, max) {
    return Math.random() * (max - min + 1) + min;
  }
  const [number, setNumber] = useState("");
  const [date, setDate] = useState("");
  const [name, setName] = useState("");
  const [cva, setCva] = useState();
  const price = randomCost(50, 2000);

  const handleSubmit = () => {
    const creditCard = { number, name, date, cva, balance: 0.0 };
    const payment = { price, creditCard };
    PostReq("http://mkb.express.edlcn/api/v1/payments", payment).then((res) =>
      console.log(res)
    );
  };

  return (
    <div className="formf">
      <div className="cost">
        <h2>Total of: {price.toFixed(2)} $</h2>
      </div>

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
          value={cva}
          onChange={(event) => setCva(event.target.value)}
        />
      </form>
      <button onClick={handleSubmit}>Pay</button>
    </div>
  );
};

export default CardInput;
