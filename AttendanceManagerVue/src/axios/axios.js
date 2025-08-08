import axios from 'axios';

const axiosInstance = axios.create();
axios.defaults.baseURL = 'http://localhost:9331'

axiosInstance.interceptors.request.use(
  config => {
    
    if(!document.cookie.split(';').some(c => c.trim().startsWith('LOGIN='))) {
          
        this.$router.push("/");
        this.$message({ message: "未登录或登录信息过期", type: "error" });
        return
      } 
    return config;
  },
  error => {
    // 对请求错误的处理
    return Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(
  response => {
    // 在响应被处理后的处理
    // 比如处理响应数据、处理错误状态等
    return response;
  },
  error => {
    // 对响应错误的处理
    return Promise.reject(error);
  }
);

export default axiosInstance;