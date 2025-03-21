@echo off
setlocal enabledelayedexpansion

set "macAddress=F4:96:34:7A:A7:29"
set "ipAddress="
set "subnet="

for /f "tokens=2 delims=:" %%A in ('ipconfig ^| find "IPv4 Address"') do (
    for /f "tokens=1,2,3 delims=." %%B in ("%%A") do (
        set "subnet=%%B.%%C.%%D"
    )
)

echo Scanning all IPs in subnet: %subnet%.x ...

for /f "tokens=1-6 delims= " %%A in ('nmap -sn %subnet%.0/24') do (
    if "%%A %%B %%C %%D" == "Nmap scan report for" (
        if "%%F" == "" (
            set ip=%%E
        ) else (
            set ip=%%F
            set ip=!ip:~1,-1!
        )
    )
    if "%%A %%B %%C" == "MAC Address: !macAddress!" (
        set ipAddress=!ip!
        goto :found
    )
)

if "%ipAddress%"=="" (
    echo Could not find Hub IP!
    endlocal
    pause
    exit /b 1
)

:found
echo Hub IP found: %ipAddress%

java -jar selenium-server-4.29.0.jar node --hub http://%ipAddress%:4444

endlocal
pause
