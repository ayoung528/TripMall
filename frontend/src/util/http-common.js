import axios from "axios";
const BACK_URL = process.env.VUE_APP_BACK_URL;

export default axios.create({
    baseURL: BACK_URL,
    headers: {
        "Content-type": "application/json"
    }
});