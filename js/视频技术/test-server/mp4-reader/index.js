import MSE from './controller'

var assetURL = 'http://localhost:8080/zhihu2018_sd.mp4';
var video = document.querySelector('video')

export default function() {
    var mse = new MSE(video, assetURL)
    mse.init()
}