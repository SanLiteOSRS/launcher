[Setup]
AppName=SanLite
AppPublisher=SanLite
UninstallDisplayName=SanLite
AppVersion=Launcher ${project.version}
AppSupportURL=https://runelite.net/
DefaultDirName={localappdata}\SanLite

; ~30 mb for the repo the launcher downloads
ExtraDiskSpaceRequired=30000000
ArchitecturesAllowed=x86 x64
PrivilegesRequired=lowest

WizardSmallImageFile=sanlite_small.bmp
SetupIconFile=${basedir}/sanlite.ico
UninstallDisplayIcon={app}\SanLite.exe

Compression=lzma2
SolidCompression=yes

OutputDir=${basedir}
OutputBaseFilename=SanLiteSetup32

[Tasks]
Name: DesktopIcon; Description: "Create a &desktop icon";

[Files]
Source: "${project.build.directory}\native\win32\SanLite.exe"; DestDir: "{app}"
Source: "${project.build.directory}\native\win32\SanLite.jar"; DestDir: "{app}"
Source: "${project.build.directory}\native\win32\config.json"; DestDir: "{app}"
Source: "${project.build.directory}\native\win32\jre\*"; DestDir: "{app}\jre"; Flags: recursesubdirs
Source: "${project.build.directory}\native\win32\jre\bin\msvcr120.dll"; DestDir: "{app}"

[Icons]
; start menu
Name: "{userprograms}\SanLite"; Filename: "{app}\SanLite.exe"
Name: "{commondesktop}\SanLite"; Filename: "{app}\SanLite.exe"; Tasks: DesktopIcon

[Run]
Filename: "{app}\SanLite.exe"; Description: "&Open SanLite"; Flags: postinstall skipifsilent nowait

[UninstallDelete]
Type: filesandordirs; Name: "{%USERPROFILE}\.sanlite\repository"
Type: filesandordirs; Name: "{%USERPROFILE}\.sanlite\repository2"