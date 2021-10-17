**This repo is part of WebRTC examples, see also:**

WebRTC Signaling server in Ktor - https://github.com/artem-bagritsevich/WebRTCKtorSignalingServerExample
How to build WebRTC for Android - https://github.com/artem-bagritsevich/AndroidWebRTC

Here is example of P2P Video client with WebRTC under the hood. 
**This is just example created in educational purpose, so don't use this code in production!**

How to run use the app:

- First of all app you will need to run Signaling server (https://github.com/artem-bagritsevich/WebRTCKtorSignalingServerExample)

- After server us running, return to this repo and update `SIGNALING_URL` in `SignalingClient` with server ip address.

Example: `private const val SIGNALING_URL = "ws://192.168.31.53:8080/rtc"`

In the example above **192.168.31.53** is local ip address of my machine where server is running.
It will be accessible only inside local network, so only devices connected to the same network will be able to communicate via signaling.

- After everything is done install build and install the app to two android devices and run them.
