package ar.unrn.tp.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.HibernateException;
import org.hibernate.collection.spi.AbstractPersistentCollection;
import org.hibernate.metamodel.mapping.PluralAttributeMapping;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.type.Type;

public class PersistentBag extends AbstractPersistentCollection implements List {

	protected List bag;

	@Override
	public Iterator entries(CollectionPersister persister) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getIndex(Object entry, int i, CollectionPersister persister) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getElement(Object entry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSnapshotElement(Object entry, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equalsSnapshot(CollectionPersister persister) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSnapshotEmpty(Serializable snapshot) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Serializable getSnapshot(CollectionPersister persister) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean entryExists(Object entry, int i) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean needsInserting(Object entry, int i, Type elemType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean needsUpdating(Object entry, int i, Type elemType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator getDeletes(CollectionPersister persister, boolean indexIsFormula) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapper(Object collection) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void initializeFromCache(CollectionPersister persister, Object disassembled, Object owner) {
		// TODO Auto-generated method stub

	}

	@Override
	public void injectLoadedState(PluralAttributeMapping attributeMapping, List loadingState) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object disassemble(CollectionPersister persister) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initializeEmptyCollection(CollectionPersister persister) {
		// TODO Auto-generated method stub

	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Object e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object set(int index, Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, Object element) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean empty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection getOrphans(Serializable snapshot, String entityName) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}
}
