export const validateMail = (mail) => {
  var re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return re.test(mail);
};

export const validatePassword = (password) => {
  var big = false;
  var small = false;
  var number = false;
  for (var x of password) {
    if (x <= "Z" && x >= "A") big = true;
    else if (x <= "z" && x >= "a") small = true;
    else if (x >= "0" && x <= "9") number = true;
  }
  return big && small && number;
};

export const validateCreditCard = (cc) => {
  if (cc.length != 16) return false;
  for (var x of cc) {
    if (!(x >= "0" && x <= "9")) return false;
  }
  return true;
};

export const validateCvv = (cva) => {
  if (cva.length != 3) return false;
  for (var x of cva) {
    if (!(x >= "0" && x <= "9")) return false;
  }
  return true;
};
