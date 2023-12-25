import requests
import schedule
import time

endpoint_url = "http://endpoint:8082/v1"
generate_token_endpoint = f"{endpoint_url}/token/generate"
verify_token_endpoint = f"{endpoint_url}/token/verify"

def get_token():
    try:
        response = requests.get(generate_token_endpoint)
        if response.status_code == 200:
            return response.text
        else:
            print(f"Failed to get token: {response.text}")
            return None
    except Exception as e:
        print(f"Error getting token: {e}")
        return None

def verify_token(token):
    try:
        headers = {'token': token}
        response = requests.post(verify_token_endpoint, headers=headers)
        print(f"Verification result: {response.text}")
    except Exception as e:
        print(f"Error verifying token: {e}")

def job():
    global token
    if token:
        verify_token(token)

print("Starting the client...")

# Get the token once
token = get_token()

# time.sleep(60)

if token:
    print(f"Received token: {token}")

# Schedule the verification job every 10 seconds
schedule.every(10).seconds.do(job)

# Keep running the schedule
while True:
    schedule.run_pending()
    time.sleep(1)
