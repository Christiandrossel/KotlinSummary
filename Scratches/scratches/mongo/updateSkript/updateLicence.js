/**
 * ich möchte in der mongo shell ein update durchführen. dazu möchte ich in die collection reservationTest gehen und
 * alle reservationIds als String holen deren stae="REJECTED oder REVOKED ist. in der collection licenceTest möchte
 * ich nun alle finden deren array activeReservation die id beinhaltet. diese id sollen aus dem array rausgelöscht werden.
 */
const reservationIds = db.reservationTest.find({state: {$in: ["REJECTED", "REVOKED"]}}).map(r => r._id);
// count reservation ids
// reservationIds.length;
db.licenceTest.updateMany({activeReservations: {$in: reservationIds}}, {$pull: {activeReservations: {$in: reservationIds}}});