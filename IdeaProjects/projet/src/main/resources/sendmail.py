import smtplib
import sys
from email.mime.text import MIMEText

def send_email():
    sender_email = "bgbassem8@gmail.com"
    receiver_email = sys.argv[1]
    subject = "🎉🎉🎉 Bienvenue 🎉🎉🎉"
    body = "Bienvenue sur **Sportify** ! 🎉"
    
    msg = MIMEText(body)
    msg['Subject'] = subject
    msg['From'] = sender_email
    msg['To'] = receiver_email

    try:
        server = smtplib.SMTP('smtp.gmail.com', 587)
        server.starttls()
        server.login(sender_email, 'ngwj plen jvfv gczo')  # Use app password instead of Gmail password
        server.sendmail(sender_email, receiver_email, msg.as_string())
        server.quit()
        print("Email sent successfully!")
    except Exception as e:
        print(f"Error sending email: {e}")

if __name__ == "__main__":
    send_email()
