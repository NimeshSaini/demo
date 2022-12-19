package com.utkarshnew.android.Dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.utkarshnew.android.Model.Courselist;
import com.utkarshnew.android.Model.ExtendValidity;
import com.utkarshnew.android.TypeConverter.Converters;
import com.utkarshnew.android.table.MycourseTable;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class MycourseDao_Impl implements MycourseDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfMycourseTable;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfMycourseTable;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfMycourseTable;

  private final SharedSQLiteStatement __preparedStmtOfDeletedata;

  private final SharedSQLiteStatement __preparedStmtOfDelete;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_course_lastread;

  private final SharedSQLiteStatement __preparedStmtOfUpdate_course_lastread_1;

  public MycourseDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMycourseTable = new EntityInsertionAdapter<MycourseTable>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `mycoursetable`(`autoid`,`id`,`title`,`isSelect`,`isExpand`,`batch_id`,`cover_image`,`expiry_date`,`purchase_date`,`mrp`,`txn_id`,`lastread`,`userid`,`batchtype`,`delete`,`prices`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MycourseTable value) {
        stmt.bindLong(1, value.getAutoid());
        if (value.getId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getId());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTitle());
        }
        if (value.getIsSelect() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getIsSelect());
        }
        if (value.getIsExpand() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getIsExpand());
        }
        if (value.getBatch_id() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getBatch_id());
        }
        if (value.getCover_image() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCover_image());
        }
        if (value.getExpiry_date() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getExpiry_date());
        }
        if (value.getPurchase_date() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getPurchase_date());
        }
        if (value.getMrp() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getMrp());
        }
        if (value.getTxn_id() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getTxn_id());
        }
        if (value.getLastread() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getLastread());
        }
        if (value.getUserid() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getUserid());
        }
        if (value.getBatchtype() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getBatchtype());
        }
        stmt.bindLong(15, value.getDelete());
        final String _tmp;
        _tmp = Converters.fromArrayList(value.getPrices());
        if (_tmp == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, _tmp);
        }
      }
    };
    this.__deletionAdapterOfMycourseTable = new EntityDeletionOrUpdateAdapter<MycourseTable>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `mycoursetable` WHERE `autoid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MycourseTable value) {
        stmt.bindLong(1, value.getAutoid());
      }
    };
    this.__updateAdapterOfMycourseTable = new EntityDeletionOrUpdateAdapter<MycourseTable>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `mycoursetable` SET `autoid` = ?,`id` = ?,`title` = ?,`isSelect` = ?,`isExpand` = ?,`batch_id` = ?,`cover_image` = ?,`expiry_date` = ?,`purchase_date` = ?,`mrp` = ?,`txn_id` = ?,`lastread` = ?,`userid` = ?,`batchtype` = ?,`delete` = ?,`prices` = ? WHERE `autoid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MycourseTable value) {
        stmt.bindLong(1, value.getAutoid());
        if (value.getId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getId());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTitle());
        }
        if (value.getIsSelect() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getIsSelect());
        }
        if (value.getIsExpand() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getIsExpand());
        }
        if (value.getBatch_id() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getBatch_id());
        }
        if (value.getCover_image() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getCover_image());
        }
        if (value.getExpiry_date() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getExpiry_date());
        }
        if (value.getPurchase_date() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getPurchase_date());
        }
        if (value.getMrp() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getMrp());
        }
        if (value.getTxn_id() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getTxn_id());
        }
        if (value.getLastread() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getLastread());
        }
        if (value.getUserid() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getUserid());
        }
        if (value.getBatchtype() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getBatchtype());
        }
        stmt.bindLong(15, value.getDelete());
        final String _tmp;
        _tmp = Converters.fromArrayList(value.getPrices());
        if (_tmp == null) {
          stmt.bindNull(16);
        } else {
          stmt.bindString(16, _tmp);
        }
        stmt.bindLong(17, value.getAutoid());
      }
    };
    this.__preparedStmtOfDeletedata = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM mycoursetable";
        return _query;
      }
    };
    this.__preparedStmtOfDelete = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "Delete from mycoursetable where id = ? AND txn_id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_course_lastread = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE mycoursetable SET lastread=? WHERE id = ? AND  userid = ? AND batchtype =?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate_course_lastread_1 = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE mycoursetable SET lastread=? WHERE id = ? AND  userid = ? ";
        return _query;
      }
    };
  }

  @Override
  public long addUser(MycourseTable getProfileTable) {
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfMycourseTable.insertAndReturnId(getProfileTable);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteUser(MycourseTable userTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfMycourseTable.handle(userTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateUser(MycourseTable userTable) {
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfMycourseTable.handle(userTable);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletedata() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletedata.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletedata.release(_stmt);
    }
  }

  @Override
  public void delete(String course_id, String txn_id) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfDelete.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (course_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, course_id);
      }
      _argIndex = 2;
      if (txn_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, txn_id);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDelete.release(_stmt);
    }
  }

  @Override
  public int update_course_lastread(String lastread_timestamp, String c_id, String userid,
      String batch_type) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_course_lastread.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (lastread_timestamp == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, lastread_timestamp);
      }
      _argIndex = 2;
      if (c_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, c_id);
      }
      _argIndex = 3;
      if (userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, userid);
      }
      _argIndex = 4;
      if (batch_type == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, batch_type);
      }
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate_course_lastread.release(_stmt);
    }
  }

  @Override
  public int update_course_lastread(String lastread_timestamp, String c_id, String userid) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate_course_lastread_1.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (lastread_timestamp == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, lastread_timestamp);
      }
      _argIndex = 2;
      if (c_id == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, c_id);
      }
      _argIndex = 3;
      if (userid == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, userid);
      }
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate_course_lastread_1.release(_stmt);
    }
  }

  @Override
  public List<MycourseTable> getAllUser() {
    final String _sql = "SELECT * FROM mycoursetable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfAutoid = _cursor.getColumnIndexOrThrow("autoid");
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
      final int _cursorIndexOfIsSelect = _cursor.getColumnIndexOrThrow("isSelect");
      final int _cursorIndexOfIsExpand = _cursor.getColumnIndexOrThrow("isExpand");
      final int _cursorIndexOfBatchId = _cursor.getColumnIndexOrThrow("batch_id");
      final int _cursorIndexOfCoverImage = _cursor.getColumnIndexOrThrow("cover_image");
      final int _cursorIndexOfExpiryDate = _cursor.getColumnIndexOrThrow("expiry_date");
      final int _cursorIndexOfPurchaseDate = _cursor.getColumnIndexOrThrow("purchase_date");
      final int _cursorIndexOfMrp = _cursor.getColumnIndexOrThrow("mrp");
      final int _cursorIndexOfTxnId = _cursor.getColumnIndexOrThrow("txn_id");
      final int _cursorIndexOfLastread = _cursor.getColumnIndexOrThrow("lastread");
      final int _cursorIndexOfUserid = _cursor.getColumnIndexOrThrow("userid");
      final int _cursorIndexOfBatchtype = _cursor.getColumnIndexOrThrow("batchtype");
      final int _cursorIndexOfDelete = _cursor.getColumnIndexOrThrow("delete");
      final int _cursorIndexOfPrices = _cursor.getColumnIndexOrThrow("prices");
      final List<MycourseTable> _result = new ArrayList<MycourseTable>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final MycourseTable _item;
        _item = new MycourseTable();
        final int _tmpAutoid;
        _tmpAutoid = _cursor.getInt(_cursorIndexOfAutoid);
        _item.setAutoid(_tmpAutoid);
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpIsSelect;
        _tmpIsSelect = _cursor.getString(_cursorIndexOfIsSelect);
        _item.setIsSelect(_tmpIsSelect);
        final String _tmpIsExpand;
        _tmpIsExpand = _cursor.getString(_cursorIndexOfIsExpand);
        _item.setIsExpand(_tmpIsExpand);
        final String _tmpBatch_id;
        _tmpBatch_id = _cursor.getString(_cursorIndexOfBatchId);
        _item.setBatch_id(_tmpBatch_id);
        final String _tmpCover_image;
        _tmpCover_image = _cursor.getString(_cursorIndexOfCoverImage);
        _item.setCover_image(_tmpCover_image);
        final String _tmpExpiry_date;
        _tmpExpiry_date = _cursor.getString(_cursorIndexOfExpiryDate);
        _item.setExpiry_date(_tmpExpiry_date);
        final String _tmpPurchase_date;
        _tmpPurchase_date = _cursor.getString(_cursorIndexOfPurchaseDate);
        _item.setPurchase_date(_tmpPurchase_date);
        final String _tmpMrp;
        _tmpMrp = _cursor.getString(_cursorIndexOfMrp);
        _item.setMrp(_tmpMrp);
        final String _tmpTxn_id;
        _tmpTxn_id = _cursor.getString(_cursorIndexOfTxnId);
        _item.setTxn_id(_tmpTxn_id);
        final String _tmpLastread;
        _tmpLastread = _cursor.getString(_cursorIndexOfLastread);
        _item.setLastread(_tmpLastread);
        final String _tmpUserid;
        _tmpUserid = _cursor.getString(_cursorIndexOfUserid);
        _item.setUserid(_tmpUserid);
        final String _tmpBatchtype;
        _tmpBatchtype = _cursor.getString(_cursorIndexOfBatchtype);
        _item.setBatchtype(_tmpBatchtype);
        final int _tmpDelete;
        _tmpDelete = _cursor.getInt(_cursorIndexOfDelete);
        _item.setDelete(_tmpDelete);
        final List<ExtendValidity> _tmpPrices;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfPrices);
        _tmpPrices = Converters.fromString(_tmp);
        _item.setPrices(_tmpPrices);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<String> getAllcourseid(String userid) {
    final String _sql = "SELECT  DISTINCT id  FROM mycoursetable  where  userid=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final List<String> _result = new ArrayList<String>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final String _item;
        _item = _cursor.getString(0);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Courselist> getFreecourse(String mrp, String batch_type) {
    final String _sql = "SELECT * FROM mycoursetable where  mrp = ? AND batchtype=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (mrp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, mrp);
    }
    _argIndex = 2;
    if (batch_type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, batch_type);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
      final int _cursorIndexOfIsSelect = _cursor.getColumnIndexOrThrow("isSelect");
      final int _cursorIndexOfIsExpand = _cursor.getColumnIndexOrThrow("isExpand");
      final int _cursorIndexOfBatchId = _cursor.getColumnIndexOrThrow("batch_id");
      final int _cursorIndexOfCoverImage = _cursor.getColumnIndexOrThrow("cover_image");
      final int _cursorIndexOfExpiryDate = _cursor.getColumnIndexOrThrow("expiry_date");
      final int _cursorIndexOfPurchaseDate = _cursor.getColumnIndexOrThrow("purchase_date");
      final int _cursorIndexOfMrp = _cursor.getColumnIndexOrThrow("mrp");
      final int _cursorIndexOfTxnId = _cursor.getColumnIndexOrThrow("txn_id");
      final int _cursorIndexOfLastread = _cursor.getColumnIndexOrThrow("lastread");
      final int _cursorIndexOfDelete = _cursor.getColumnIndexOrThrow("delete");
      final int _cursorIndexOfPrices = _cursor.getColumnIndexOrThrow("prices");
      final List<Courselist> _result = new ArrayList<Courselist>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Courselist _item;
        _item = new Courselist();
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final boolean _tmpIsSelect;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsSelect);
        _tmpIsSelect = _tmp != 0;
        _item.setSelect(_tmpIsSelect);
        final boolean _tmpIsExpand;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsExpand);
        _tmpIsExpand = _tmp_1 != 0;
        _item.setExpand(_tmpIsExpand);
        final String _tmpBatch_id;
        _tmpBatch_id = _cursor.getString(_cursorIndexOfBatchId);
        _item.setBatch_id(_tmpBatch_id);
        final String _tmpCover_image;
        _tmpCover_image = _cursor.getString(_cursorIndexOfCoverImage);
        _item.setCover_image(_tmpCover_image);
        final String _tmpExpiry_date;
        _tmpExpiry_date = _cursor.getString(_cursorIndexOfExpiryDate);
        _item.setExpiry_date(_tmpExpiry_date);
        final String _tmpPurchase_date;
        _tmpPurchase_date = _cursor.getString(_cursorIndexOfPurchaseDate);
        _item.setPurchase_date(_tmpPurchase_date);
        final String _tmpMrp;
        _tmpMrp = _cursor.getString(_cursorIndexOfMrp);
        _item.setMrp(_tmpMrp);
        final String _tmpTxn_id;
        _tmpTxn_id = _cursor.getString(_cursorIndexOfTxnId);
        _item.setTxn_id(_tmpTxn_id);
        final String _tmpLastread;
        _tmpLastread = _cursor.getString(_cursorIndexOfLastread);
        _item.setLastread(_tmpLastread);
        final int _tmpDelete;
        _tmpDelete = _cursor.getInt(_cursorIndexOfDelete);
        _item.setDelete(_tmpDelete);
        final List<ExtendValidity> _tmpPrices;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfPrices);
        _tmpPrices = Converters.fromString(_tmp_2);
        _item.setPrices(_tmpPrices);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Courselist> getpaidcourse(String batch_type) {
    final String _sql = "SELECT * FROM mycoursetable where  batchtype=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (batch_type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, batch_type);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
      final int _cursorIndexOfIsSelect = _cursor.getColumnIndexOrThrow("isSelect");
      final int _cursorIndexOfIsExpand = _cursor.getColumnIndexOrThrow("isExpand");
      final int _cursorIndexOfBatchId = _cursor.getColumnIndexOrThrow("batch_id");
      final int _cursorIndexOfCoverImage = _cursor.getColumnIndexOrThrow("cover_image");
      final int _cursorIndexOfExpiryDate = _cursor.getColumnIndexOrThrow("expiry_date");
      final int _cursorIndexOfPurchaseDate = _cursor.getColumnIndexOrThrow("purchase_date");
      final int _cursorIndexOfMrp = _cursor.getColumnIndexOrThrow("mrp");
      final int _cursorIndexOfTxnId = _cursor.getColumnIndexOrThrow("txn_id");
      final int _cursorIndexOfLastread = _cursor.getColumnIndexOrThrow("lastread");
      final int _cursorIndexOfDelete = _cursor.getColumnIndexOrThrow("delete");
      final int _cursorIndexOfPrices = _cursor.getColumnIndexOrThrow("prices");
      final List<Courselist> _result = new ArrayList<Courselist>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Courselist _item;
        _item = new Courselist();
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final boolean _tmpIsSelect;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsSelect);
        _tmpIsSelect = _tmp != 0;
        _item.setSelect(_tmpIsSelect);
        final boolean _tmpIsExpand;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsExpand);
        _tmpIsExpand = _tmp_1 != 0;
        _item.setExpand(_tmpIsExpand);
        final String _tmpBatch_id;
        _tmpBatch_id = _cursor.getString(_cursorIndexOfBatchId);
        _item.setBatch_id(_tmpBatch_id);
        final String _tmpCover_image;
        _tmpCover_image = _cursor.getString(_cursorIndexOfCoverImage);
        _item.setCover_image(_tmpCover_image);
        final String _tmpExpiry_date;
        _tmpExpiry_date = _cursor.getString(_cursorIndexOfExpiryDate);
        _item.setExpiry_date(_tmpExpiry_date);
        final String _tmpPurchase_date;
        _tmpPurchase_date = _cursor.getString(_cursorIndexOfPurchaseDate);
        _item.setPurchase_date(_tmpPurchase_date);
        final String _tmpMrp;
        _tmpMrp = _cursor.getString(_cursorIndexOfMrp);
        _item.setMrp(_tmpMrp);
        final String _tmpTxn_id;
        _tmpTxn_id = _cursor.getString(_cursorIndexOfTxnId);
        _item.setTxn_id(_tmpTxn_id);
        final String _tmpLastread;
        _tmpLastread = _cursor.getString(_cursorIndexOfLastread);
        _item.setLastread(_tmpLastread);
        final int _tmpDelete;
        _tmpDelete = _cursor.getInt(_cursorIndexOfDelete);
        _item.setDelete(_tmpDelete);
        final List<ExtendValidity> _tmpPrices;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfPrices);
        _tmpPrices = Converters.fromString(_tmp_2);
        _item.setPrices(_tmpPrices);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean isRecordExistsUserId(String userid, String batch_type, String id) {
    final String _sql = "SELECT EXISTS(SELECT * FROM MycourseTable WHERE userid = ? AND batchtype = ?  AND id = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
    }
    _argIndex = 2;
    if (batch_type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, batch_type);
    }
    _argIndex = 3;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean isRecordExists(String userid) {
    final String _sql = "SELECT EXISTS(SELECT * FROM MycourseTable WHERE userid = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Courselist getuser(String userid, String id, String batch_type) {
    final String _sql = "SELECT * FROM mycoursetable where  userid = ? AND batchtype = ?  AND id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    if (userid == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userid);
    }
    _argIndex = 2;
    if (batch_type == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, batch_type);
    }
    _argIndex = 3;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
      final int _cursorIndexOfTitle = _cursor.getColumnIndexOrThrow("title");
      final int _cursorIndexOfIsSelect = _cursor.getColumnIndexOrThrow("isSelect");
      final int _cursorIndexOfIsExpand = _cursor.getColumnIndexOrThrow("isExpand");
      final int _cursorIndexOfBatchId = _cursor.getColumnIndexOrThrow("batch_id");
      final int _cursorIndexOfCoverImage = _cursor.getColumnIndexOrThrow("cover_image");
      final int _cursorIndexOfExpiryDate = _cursor.getColumnIndexOrThrow("expiry_date");
      final int _cursorIndexOfPurchaseDate = _cursor.getColumnIndexOrThrow("purchase_date");
      final int _cursorIndexOfMrp = _cursor.getColumnIndexOrThrow("mrp");
      final int _cursorIndexOfTxnId = _cursor.getColumnIndexOrThrow("txn_id");
      final int _cursorIndexOfLastread = _cursor.getColumnIndexOrThrow("lastread");
      final int _cursorIndexOfDelete = _cursor.getColumnIndexOrThrow("delete");
      final int _cursorIndexOfPrices = _cursor.getColumnIndexOrThrow("prices");
      final Courselist _result;
      if(_cursor.moveToFirst()) {
        _result = new Courselist();
        final String _tmpId;
        _tmpId = _cursor.getString(_cursorIndexOfId);
        _result.setId(_tmpId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _result.setTitle(_tmpTitle);
        final boolean _tmpIsSelect;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfIsSelect);
        _tmpIsSelect = _tmp != 0;
        _result.setSelect(_tmpIsSelect);
        final boolean _tmpIsExpand;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfIsExpand);
        _tmpIsExpand = _tmp_1 != 0;
        _result.setExpand(_tmpIsExpand);
        final String _tmpBatch_id;
        _tmpBatch_id = _cursor.getString(_cursorIndexOfBatchId);
        _result.setBatch_id(_tmpBatch_id);
        final String _tmpCover_image;
        _tmpCover_image = _cursor.getString(_cursorIndexOfCoverImage);
        _result.setCover_image(_tmpCover_image);
        final String _tmpExpiry_date;
        _tmpExpiry_date = _cursor.getString(_cursorIndexOfExpiryDate);
        _result.setExpiry_date(_tmpExpiry_date);
        final String _tmpPurchase_date;
        _tmpPurchase_date = _cursor.getString(_cursorIndexOfPurchaseDate);
        _result.setPurchase_date(_tmpPurchase_date);
        final String _tmpMrp;
        _tmpMrp = _cursor.getString(_cursorIndexOfMrp);
        _result.setMrp(_tmpMrp);
        final String _tmpTxn_id;
        _tmpTxn_id = _cursor.getString(_cursorIndexOfTxnId);
        _result.setTxn_id(_tmpTxn_id);
        final String _tmpLastread;
        _tmpLastread = _cursor.getString(_cursorIndexOfLastread);
        _result.setLastread(_tmpLastread);
        final int _tmpDelete;
        _tmpDelete = _cursor.getInt(_cursorIndexOfDelete);
        _result.setDelete(_tmpDelete);
        final List<ExtendValidity> _tmpPrices;
        final String _tmp_2;
        _tmp_2 = _cursor.getString(_cursorIndexOfPrices);
        _tmpPrices = Converters.fromString(_tmp_2);
        _result.setPrices(_tmpPrices);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
