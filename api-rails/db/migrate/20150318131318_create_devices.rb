class CreateDevices < ActiveRecord::Migration
  def change
    create_table :devices do |t|
      t.integer :user_id
      t.string :room_id
      t.string :type_name
      t.string :type_brand
      t.string :name
t.references :room, index: true
t.references :user, index: true
      t.timestamps
    end
    add_foreign_key :devices, :rooms
    add_foreign_key :devices, :users
  end
end
