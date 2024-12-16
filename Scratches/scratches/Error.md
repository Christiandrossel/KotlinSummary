org.springframework.data.mapping.model.MappingInstantiationException: Failed to instantiate
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.ComposedLicenceEntity using
constructor fun `<init>`(kotlin.String,
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.ExternalIdentificationEntity, kotlin.String,
net.avgl.ekz.onleihe.inventoryapplication.licence_service.model.licence.references.LicenceProductReference,
kotlin.collections.List<net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.SupplierEntity>,
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.LicenceStateEntity,
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.LicenceModelEntity,
kotlin.collections.List<
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.SimpleLicenceEntity>,
java.time.Instant?, java.time.Instant?,
net.avgl.ekz.onleihe.inventoryapplication.licence_service.model.licence.availability.LicenceAvailability,
kotlin.collections.List<kotlin.String>):
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.ComposedLicenceEntity with arguments
67332e42916b5103d9217a7a,ExternalIdentificationEntity(licenceId=38254358, libraryId=1578)
,670f876520922f2e834bfc8a,BasicProductReference(
productId=670916b5e0c0253d15100a87),[SupplierEntity(externalId=355105813, datasourceId=65c24322673f3d10bd629d40, displayName=readbox, companyName=readbox Auslieferung)]
,ACTIVE,XL_SINGLE_CLASS_MEDIA_LICENCE,[BasicLicenceEntity(id='67332e42916b5103d9217a7b',
externalIds=ExternalIdentificationEntity(licenceId=38254358, libraryId=1578), ownerId='670f876520922f2e834bfc8a',
productReference='BasicProductReference(productId=670916b5e0c0253d15100a87)',
supplier=[SupplierEntity(externalId=355105813, datasourceId=65c24322673f3d10bd629d40, displayName=readbox, companyName=readbox Auslieferung)],
state=ACTIVE, activeLends=[6737814f25f0bf5688fc1a9a, 674847af0fadaa2d128b80ae], totalLends=4,
timeBasedRestrictions=TimeBasedRestrictionsEntity(startDate=2018-01-16T19:04:52.917Z, endDate=null,
maxLendingDuration=null, startDateUnlimitedUse=null), contingentBasedRestrictions=ContingentBasedRestrictionsEntity(
contingent=20, maxNumberOfReservations=null, maxNumberOfActiveLends=null),
readingBasedRestrictions=ReadingBasedRestrictionsEntity(isPrintingLowResolutionAllowed=true, isCopyingAllowed=true,
isPrintingAllowed=true, maxNumberOfParallelDevices=7), createdAt=null, updated=null), BasicLicenceEntity(
id='67332e42916b5103d9217a7c', externalIds=ExternalIdentificationEntity(licenceId=38254358, libraryId=1578),
ownerId='670f876520922f2e834bfc8a', productReference='BasicProductReference(productId=670916b5e0c0253d15100a87)',
supplier=[SupplierEntity(externalId=355105813, datasourceId=65c24322673f3d10bd629d40, displayName=readbox, companyName=readbox Auslieferung)],
state=ACTIVE, activeLends=[], totalLends=0, timeBasedRestrictions=TimeBasedRestrictionsEntity(startDate=2018-01-16T19:
04:52.917Z, endDate=null, maxLendingDuration=null, startDateUnlimitedUse=null),
contingentBasedRestrictions=ContingentBasedRestrictionsEntity(contingent=null, maxNumberOfReservations=null,
maxNumberOfActiveLends=2), readingBasedRestrictions=ReadingBasedRestrictionsEntity(isPrintingLowResolutionAllowed=true,
isCopyingAllowed=true, isPrintingAllowed=true, maxNumberOfParallelDevices=7), createdAt=null, updated=null)]
,2024-11-12T10:30:26.708Z,2024-11-28T10:36:32.130Z,LicenceAvailability(isUnlimited=false, availability=18,
availabilityByProduct={670916b5e0c0253d15100a87=AvailabilityInformation(isUnlimited=false, availability=18)})
,null,0,null



org.springframework.data.mapping.model.MappingInstantiationException: Failed to instantiate
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.ComposedLicenceEntity using
constructor fun `<init>`(kotlin.String,
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.ExternalIdentificationEntity, kotlin.String,
net.avgl.ekz.onleihe.inventoryapplication.licence_service.model.licence.references.LicenceProductReference,
kotlin.collections.List<net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.SupplierEntity>,
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.LicenceStateEntity,
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.LicenceModelEntity,
kotlin.collections.List<
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.SimpleLicenceEntity>,
java.time.Instant?, java.time.Instant?,
net.avgl.ekz.onleihe.inventoryapplication.licence_service.model.licence.availability.LicenceAvailability,
kotlin.collections.List<kotlin.String>):
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.ComposedLicenceEntity with arguments
671652c41e6a4e71b6a3dcbe,ExternalIdentificationEntity(licenceId=39056158, libraryId=148)
,670f7fec20922f2e834bfc6f,BasicProductReference(
productId=670917b553e7dc00703b5a25),[SupplierEntity(externalId=393629082, datasourceId=65c24337673f3d10bd629d9c, displayName=readbox_befr, companyName=readbox Auslieferung befristet)]
,ACTIVE,XL_SINGLE_CLASS_MEDIA_LICENCE,[BasicLicenceEntity(id='671652c41e6a4e71b6a3dcbf',
externalIds=ExternalIdentificationEntity(licenceId=39056158, libraryId=148), ownerId='670f7fec20922f2e834bfc6f',
productReference='BasicProductReference(productId=670917b553e7dc00703b5a25)',
supplier=[SupplierEntity(externalId=393629082, datasourceId=65c24337673f3d10bd629d9c, displayName=readbox_befr, companyName=readbox Auslieferung befristet)],
state=ACTIVE,
activeLends=[6724fcbd4e00fa39fcdf1338, 6728cf67b66fe767ace8bdcf, 6728fc00b66fe767ace8d688, 672f9d1b92212625548927c1, 672fc10a75e28e10d0d8caba, 6734cc7d916b5103d923b52e, 6734f57aaf0c43203e92ee42, 6735e70a97d4be629fb0998e, 673b3a47e331e73ca7be52f4, 674207f7990cd45cab3bb248, 674214249e10a156777bbc15, 67439f25990cd45cab3c1223],
totalLends=20, timeBasedRestrictions=TimeBasedRestrictionsEntity(startDate=2019-05-27T22:00:00Z, endDate=2026-05-26T22:
00:00Z, maxLendingDuration=null, startDateUnlimitedUse=null),
contingentBasedRestrictions=ContingentBasedRestrictionsEntity(contingent=20, maxNumberOfReservations=null,
maxNumberOfActiveLends=null), readingBasedRestrictions=ReadingBasedRestrictionsEntity(
isPrintingLowResolutionAllowed=false, isCopyingAllowed=false, isPrintingAllowed=false, maxNumberOfParallelDevices=7),
createdAt=null, updated=null), BasicLicenceEntity(id='671652c41e6a4e71b6a3dcc0',
externalIds=ExternalIdentificationEntity(licenceId=39056158, libraryId=148), ownerId='670f7fec20922f2e834bfc6f',
productReference='BasicProductReference(productId=670917b553e7dc00703b5a25)',
supplier=[SupplierEntity(externalId=393629082, datasourceId=65c24337673f3d10bd629d9c, displayName=readbox_befr, companyName=readbox Auslieferung befristet)],
state=ACTIVE, activeLends=[6748646b0fadaa2d128b862f], totalLends=1, timeBasedRestrictions=TimeBasedRestrictionsEntity(
startDate=2019-05-27T22:00:00Z, endDate=2026-05-26T22:00:00Z, maxLendingDuration=null, startDateUnlimitedUse=null),
contingentBasedRestrictions=ContingentBasedRestrictionsEntity(contingent=null, maxNumberOfReservations=null,
maxNumberOfActiveLends=2), readingBasedRestrictions=ReadingBasedRestrictionsEntity(isPrintingLowResolutionAllowed=false,
isCopyingAllowed=false, isPrintingAllowed=false, maxNumberOfParallelDevices=7), createdAt=null, updated=null)]
,2024-10-21T13:10:28.295Z,2024-11-28T12:39:07.753Z,LicenceAvailability(isUnlimited=false, availability=1,
availabilityByProduct={670917b553e7dc00703b5a25=AvailabilityInformation(isUnlimited=false, availability=1)}),null,0,null
at
org.springframework.data.mapping.model.KotlinClassGeneratingEntityInstantiator$DefaultingKotlinClassInstantiatorAdapter.createInstance(
KotlinClassGeneratingEntityInstantiator.java:102)
at org.springframework.data.mapping.model.ClassGeneratingEntityInstantiator.createInstance(
ClassGeneratingEntityInstantiator.java:98)
Caused by: java.lang.NullPointerException: Parameter specified as non-null is null: method
net.avgl.ekz.onleihe.inventoryapplication.licence_service.database.entities.licence.ComposedLicenceEntity.<init>,
parameter activeReservations