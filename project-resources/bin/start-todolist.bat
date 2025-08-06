@echo off

echo Starting ToDoList Application...

taskkill /fi "WindowTitle eq ToDoList"
start "ToDoList" java -jar ..\..\target\todolist-runner.jar
