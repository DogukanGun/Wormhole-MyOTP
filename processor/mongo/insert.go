package mongo

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

func (db *DbExecute) Insert(data interface{}) (string, error) {
	coll := db.Client.Database(db.Database).Collection(db.Collection)
	defer func(Client *mongo.Client, ctx context.Context) {
		err := Client.Disconnect(ctx)
		if err != nil {
			fmt.Println("Could not disconnect from mongo")
		}
	}(db.Client, context.TODO())
	result, err := coll.InsertOne(context.Background(), data)
	if err != nil {
		panic(err)
	}
	return result.InsertedID.(primitive.ObjectID).Hex(), err
}
