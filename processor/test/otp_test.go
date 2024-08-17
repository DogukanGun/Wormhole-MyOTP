package test

import (
	"offchain_processor/firebase"
	"os"
	"testing"
)

func TestMustSendMessage(t *testing.T) {
	t.Setenv("TEST_TOKEN", "")
	t.Setenv("TITLE", "")
	t.Setenv("BODY", "")
	firebase.SendOTPCode(os.Getenv("TEST_TOKEN"), os.Getenv("TITLE"), os.Getenv("BODY"))
}
