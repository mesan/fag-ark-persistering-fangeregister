package no.mesan.fag.arkitektur.persistering.fangerepo.store;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.NotFoundException;

import no.mesan.fag.arkitektur.persistering.fangerepo.FangerepoConfiguration;
import no.mesan.fag.arkitektur.persistering.fangerepo.core.Fange;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class FangeMongoDBMorphiaStore implements FangeStore {

	private final Datastore ds;

	public FangeMongoDBMorphiaStore(FangerepoConfiguration configuration) {
		ds = configureDatastore(configuration);
	}

	public Datastore configureDatastore(FangerepoConfiguration configuration) {
		MongoClient client;
		try {
			client = new MongoClient(new ServerAddress(configuration.getMongohost(), configuration.getMongoport()));
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
		Morphia morphia = new Morphia();
		morphia.map(FangeMongo.class);
		return morphia.createDatastore(client, configuration.getMongodb());
	}

	@Override
	public Fange getById(String id) {
		FangeMongo fangeMongo;
		try {
			fangeMongo = ds.get(FangeMongo.class, new ObjectId(id));
		} catch (IllegalArgumentException e) {
			throw new NotFoundException(e);
		}
		checkForFoundEntity(fangeMongo);
		return fangeMongo.toFange();
	}

	@Override
	public Fange getByName(String navn) {
		FangeMongo fangeMongo = ds.find(FangeMongo.class).field("navn").contains(navn).get();
		checkForFoundEntity(fangeMongo);
		return fangeMongo.toFange();
	}

	private void checkForFoundEntity(Object entity) {
		if (entity == null) {
			throw new NotFoundException();
		}
	}

	@Override
	public Fange create(Fange fange) {
		FangeMongo fangeMongo = new FangeMongo(fange.cloneWithId(null));
		ds.save(fangeMongo);
		return fangeMongo.toFange();
	}

	@Override
	public Fange update(Fange fange) {
		FangeMongo fangeMongo;
		try {
			fangeMongo = new FangeMongo(fange);
		} catch (IllegalArgumentException e) {
			throw new NotFoundException(e);
		}
		ds.save(fangeMongo);
		return fangeMongo.toFange();
	}

	@Override
	public List<Fange> getAll() {
		Query<FangeMongo> query = ds.find(FangeMongo.class);
		return query.asList().stream().map(FangeMongo::toFange).collect(Collectors.toList());
	}

}
