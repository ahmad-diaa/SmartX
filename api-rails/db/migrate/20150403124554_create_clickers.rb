class CreateClickers < ActiveRecord::Migration
  def change
    create_table :clickers do |t|
      t.string :command
      t.integer :user_id
      t.integer :room_id
      t.string :device_id
      t.references :device, index: true
      t.references :room, index: true
      t.references :user, index: true
      t.timestamps
    end
    add_foreign_key :clickers, :devices
    add_foreign_key :clickers, :rooms
    add_foreign_key :clickers, :users

  end
end
