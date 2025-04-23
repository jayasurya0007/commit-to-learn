const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const admin = require('firebase-admin');
const app = express();
const port = process.env.PORT || 3000;
const serviceAccount = require('./serviceAccountKey.json');

// Initialize Firebase Admin
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
});

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Logging middleware
app.use((req, res, next) => {
  console.log(`${new Date().toISOString()} - ${req.method} ${req.path}`);
  next();
});

// Health check endpoint
app.get('/', (req, res) => {
  res.json({ status: 'ok', message: 'Push notification server is running' });
});

app.post('/send-notification', async (req, res) => {
  console.log('Received notification request:', req.body);

  const { message, deviceToken } = req.body;

  if (!message || !deviceToken) {
    console.log('Invalid request - missing parameters');
    return res.status(400).json({
      success: false,
      error: 'Both message and deviceToken are required'
    });
  }

  try {
    console.log('Attempting to send notification to token:', deviceToken);

    const messagePayload = {
      token: deviceToken,
      notification: {
        title: 'Push Notification',
        body: message,
      },
      data: {
        click_action: 'FLUTTER_NOTIFICATION_CLICK',
        sent_time: new Date().toISOString(),
      },
    };

    const response = await admin.messaging().send(messagePayload);

    console.log('Successfully sent message:', response);
    res.json({
      success: true,
      message: 'Notification sent successfully',
      response
    });
  } catch (error) {
    console.error('Error sending notification:', error);
    res.status(500).json({
      success: false,
      error: error.message || 'Failed to send notification'
    });
  }
});

// Error handling middleware
app.use((err, req, res, next) => {
  console.error('Server error:', err);
  res.status(500).json({ success: false, error: 'Internal server error' });
});

app.listen(port, () => {
  console.log(`Server running at http://localhost:${port}`);
});

process.on('unhandledRejection', (reason, promise) => {
  console.error('Unhandled Rejection at:', promise, 'reason:', reason);
});