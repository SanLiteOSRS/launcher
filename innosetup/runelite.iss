[Setup]
AppName=SanLite Launcher
AppPublisher=SanLite
UninstallDisplayName=SanLite
AppVersion=${project.version}
AppSupportURL=https://discord.gg/hNgWmk6
DefaultDirName={localappdata}\SanLite

; ~30 mb for the repo the launcher downloads
ExtraDiskSpaceRequired=30000000
ArchitecturesAllowed=x64
PrivilegesRequired=lowest

WizardSmallImageFile=${basedir}/innosetup/runelite_small.bmp
SetupIconFile=${basedir}/runelite.ico
UninstallDisplayIcon={app}\SanLite.exe

Compression=lzma2
SolidCompression=yes

OutputDir=${basedir}
OutputBaseFilename=SanLiteSetup

[Tasks]
Name: DesktopIcon; Description: "Create a &desktop icon";

[Files]
Source: "${basedir}\native-win64\SanLite.exe"; DestDir: "{app}"
Source: "${basedir}\native-win64\SanLite.jar"; DestDir: "{app}"
Source: "${basedir}\native\launcher_amd64.dll"; DestDir: "{app}"
Source: "${basedir}\native-win64\config.json"; DestDir: "{app}"
Source: "${basedir}\native-win64\jre\*"; DestDir: "{app}\jre"; Flags: recursesubdirs
; dependencies of jvm.dll and javaaccessbridge.dll
Source: "${basedir}\native-win64\jre\bin\vcruntime140.dll"; DestDir: "{app}"
Source: "${basedir}\native-win64\jre\bin\ucrtbase.dll"; DestDir: "{app}"
Source: "${basedir}\native-win64\jre\bin\msvcp140.dll"; DestDir: "{app}"
Source: "${basedir}\native-win64\jre\bin\api-ms-win-*.dll"; DestDir: "{app}"
Source: "${basedir}\native-win64\jre\bin\jawt.dll"; DestDir: "{app}"

[Icons]
; start menu
Name: "{userprograms}\SanLite"; Filename: "{app}\SanLite.exe"
Name: "{userdesktop}\SanLite"; Filename: "{app}\SanLite.exe"; Tasks: DesktopIcon

[Run]
Filename: "{app}\SanLite.exe"; Parameters: "--postinstall"; Flags: nowait
Filename: "{app}\SanLite.exe"; Description: "&Open SanLite"; Flags: postinstall skipifsilent nowait

[InstallDelete]
; Delete the old jvm so it doesn't try to load old stuff with the new vm and crash
Type: filesandordirs; Name: "{app}"

[UninstallDelete]
Type: filesandordirs; Name: "{%USERPROFILE}\.sanlite\repository2"