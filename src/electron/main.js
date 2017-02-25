const {app, BrowserWindow} = require('electron')
const path = require('path')
const url = require('url')

// Keep a global reference of the window object, if you don't, the window will
// be closed automatically when the JavaScript object is garbage collected.
let win;
let serverProcess;

const appUrl = 'http://localhost:8080';
const requestPromise = require('request-promise');

function startJava() {
    // For our Java app, we want to spawn it and run in background
    const spawn = require('child_process').spawn;

    serverProcess = spawn('java', ['-jar', __dirname + '/designer-app.jar']);

    // serverProcess.stdout.on('data', function (data) {
    //     console.log('Server: ' + data);
    // });
}

function createWindow() {
    // Create the browser window.
    win = new BrowserWindow({
        width: 1024,
        height: 768,
        title: 'Vaadin Designer App'
    });

    win.loadURL(appUrl);

    // Open the DevTools.
    // win.webContents.openDevTools()

    // Emitted when the window is closed.
    win.on('closed', () => {
        // Dereference the window object, usually you would store windows
        // in an array if your app supports multi windows, this is the time
        // when you should delete the corresponding element.
        win = null
    })
}

function startUI() {
    requestPromise(appUrl)
        .then(function (htmlString) {
            console.log('Server started!');
            createWindow();
        })
        .catch(function (err) {
            console.log('Waiting for the server start...');
            setTimeout(startUI, 500);
        });
}

// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', () => {
    startJava();
    startUI();
});

// Quit when all windows are closed.
app.on('window-all-closed', () => {
    // On macOS it is common for applications and their menu bar
    // to stay active until the user quits explicitly with Cmd + Q
    if (process.platform !== 'darwin') {
        app.quit()
    }
});

app.on('activate', () => {
    // On macOS it's common to re-create a window in the app when the
    // dock icon is clicked and there are no other windows open.
    if (win === null) {
        createWindow()
    }
});

app.on('quit', () => {
    // Close and destroy the JAVA process
    serverProcess.kill('SIGINT');
});
