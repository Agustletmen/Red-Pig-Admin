// 验证手机的规则
export const checkMobile = (rule, value, callback) => {
  // 手机号正则表达式
  const regMobile =
    /^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/;
  if (regMobile.test(value)) {
    // 合法的手机号
    return callback();
  }
  callback(new Error("请输入合法的手机号"));
};

// 验证邮箱的规则
export const checkEmail = (rule, value, callback) => {
  // 邮箱正则表达式
  const regEmail = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
  if (regEmail.test(value)) {
    // True 合法的邮箱
    return callback();
  }
  callback(new Error("请输入合法的邮箱"));
};
