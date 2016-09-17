cls
echo off
netsh wlan set hostednetwork mode=allow ssid=hovermouse key=12345678
netsh wlan start hostednetwork
exit