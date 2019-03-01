import axios from 'axios';

export const uploadFile = (file, url) => {
    const data = new FormData()
    data.append('file', file, file.name)

    axios.post(url, data, {
        onUploadProgress: ProgressEvent => {

        },
    })
        .then(res => {
            console.log(res.statusText)
        })
}