class CreateClickertypes < ActiveRecord::Migration
  def change
    create_table :clickertypes do |t|
      t.string :clicketype
      t.references :type, index: true
      t.integer :type_id
      t.timestamps
    end
        add_foreign_key :types

  end
end
