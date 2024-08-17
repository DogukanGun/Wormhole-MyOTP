package firebase

import (
	"context"
	firebase "firebase.google.com/go"
	"firebase.google.com/go/messaging"
	"fmt"
	"google.golang.org/api/option"
)

func SendOTPCode(registrationToken string, title string, body string) {
	opt := option.WithCredentialsFile("./serviceAccountKey.json")
	app, err := firebase.NewApp(context.Background(), nil, opt)
	if err != nil {
		fmt.Errorf("error initializing app: %v", err)
		return
	}
	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()
	message, err := app.Messaging(ctx)
	if err != nil {
		errStr := fmt.Errorf("error initializing app: %v", err)
		fmt.Printf(errStr.Error())
		return
	}
	messageObj := messaging.Message{
		Notification: &messaging.Notification{
			Title: title,
			Body:  body,
		},
		Token: registrationToken,
	}
	send, err := message.Send(ctx, &messageObj)
	if err != nil {
		return
	}
	fmt.Printf("Successful")
	fmt.Printf(send)
}
