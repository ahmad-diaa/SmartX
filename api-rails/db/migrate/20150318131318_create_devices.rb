class CreateDevices < ActiveRecord::Migration
  def change
    create_table :devices, id:false do |t|
      t.integer :user_id
      t.integer :room_id
      t.string :device_id, null: false
      t.string :status
      t.string :name
t.references :room, index: true
t.references :user, index: true
      t.timestamps
    end
    add_index :devices, :device_id, unique: true
    add_foreign_key :devices, :rooms
    add_foreign_key :devices, :users
  end
end
