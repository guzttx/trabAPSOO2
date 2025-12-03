# trabAPSOO2

cd src
$files = Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName } ; javac -d ../bin $files
java -cp ..\bin App