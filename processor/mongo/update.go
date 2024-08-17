package mongo

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
)

func (db *DbExecute) Update(newValues bson.D, filter bson.D) (int64, error) {
	coll := db.Client.Database(db.Database).Collection(db.Collection)
	defer func(Client *mongo.Client, ctx context.Context) {
		err := Client.Disconnect(ctx)
		if err != nil {
			fmt.Println("Could not disconnect from mongo")
		}
	}(db.Client, context.TODO())
	update := bson.D{{"$set", newValues}}
	result, err := coll.UpdateOne(context.TODO(), filter, update)
	if err != nil {
		panic(err)
	}
	return result.ModifiedCount, err
}
