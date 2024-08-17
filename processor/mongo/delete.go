package mongo

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
)

func (db *DbExecute) Delete(filter bson.D, first bool) (count int64, err error) {
	coll := db.Client.Database(db.Database).Collection(db.Collection)
	defer func(Client *mongo.Client, ctx context.Context) {
		err := Client.Disconnect(ctx)
		if err != nil {
			fmt.Println("Could not disconnect from mongo")
		}
	}(db.Client, context.TODO())
	if first {
		result, err := coll.DeleteOne(context.TODO(), filter)
		return result.DeletedCount, err
	}
	result, err := coll.DeleteMany(context.TODO(), filter)
	return result.DeletedCount, err
}
