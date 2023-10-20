import axios from "axios";
const server = "http://localhost:9999";

async function getData(url, param) {
  let ret = "";
  await axios.get(server + url, { params: param }).then(function (res) {
    ret = res.data;
  });
  return ret;
}

async function postData(url, param) {
  const options = {
    // 设置axios的参数
    url: server + url,
    data: param,
    method: "post"
  };

  let ret: any = "";
  await axios(options).then(function (res) {
    ret = res;
  });
  return ret;
}

async function postDataForm(url, param) {
  const options = {
    // 设置axios的参数
    url: server + url,
    data: param,
    method: "post",
    headers: { "Content-Type": "application/x-www-form-urlencoded" }
  };

  let ret: any = "";
  await axios(options).then(function (res) {
    ret = res;
  });
  return ret;
}

/**
 * async 异步处理
 * await 等待处理完成
 * @param url
 * @param param
 * @returns {Promise<string>}
 */
async function uploadOSSFile(url, param) {
  const options = {
    // 设置axios的参数
    url: server + url,
    data: param,
    method: "post",
    headers: {
      "Content-Type": "multipart/form-data"
    }
  };

  let ret = "";
  await axios(options).then(function (res) {
    ret = res.data;
  });
  return ret;
}

// export:导出变量、函数
export { getData, postData, uploadOSSFile, postDataForm, server };
