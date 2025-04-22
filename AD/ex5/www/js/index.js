document.addEventListener('deviceready', function() {
    const usernameField = document.getElementById('username');
    const passwordField = document.getElementById('password');
    const resetButton = document.getElementById('reset');
    const submitButton = document.getElementById('submit');

    resetButton.addEventListener('click', function() {
      usernameField.value = '';
      passwordField.value = '';
    });
 
    submitButton.addEventListener('click', function() {
      const username = usernameField.value;
      const password = passwordField.value;
     
      if (username && password) {
        alert('Login successful!');
      } else {
        alert('Please enter both username and password.');
      }
    });
  });
