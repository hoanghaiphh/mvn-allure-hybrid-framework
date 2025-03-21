#!/bin/bash

macAddress="F4:96:34:7A:A7:29"
ipAddress=""

subnet=$(ipconfig getifaddr en0 | awk '{print $1}' | cut -d. -f1-3)
found=0

echo "Scanning all IPs in subnet: $subnet.x ..."

results=$(sudo nmap -sn "$subnet.0/24")

while read -r line; do
    if [[ $line =~ ^Nmap\ scan\ report\ for\ (.+)$ ]]; then
        fullLine="${BASH_REMATCH[1]}"
        if [[ $fullLine =~ \((.+)\) ]]; then
            ipAddress="${BASH_REMATCH[1]}"
        else
            ipAddress="$fullLine"
        fi
    fi

    if [[ $line =~ ^MAC\ Address:\ ($macAddress) ]]; then
        echo "Hub IP found: $ipAddress"
        java -jar selenium-server-4.29.0.jar node --hub http://$ipAddress:4444
        found=1
        break
    fi
done <<< "$results"

if [[ $found -eq 0 ]]; then
    echo "Could not find Hub IP!"
    exit 1
fi
