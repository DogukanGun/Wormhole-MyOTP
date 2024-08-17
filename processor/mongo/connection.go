package mongo

import (
	"context"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"os"
)

func (db *DbExecute) Connect() (err error) {
	uri := os.Getenv("MONGODB_URI")
	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()
	client, err := mongo.Connect(ctx, options.Client().
		ApplyURI(uri))
	db.Client = client
	return
}
