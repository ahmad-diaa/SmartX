class CreateTypes < ActiveRecord::Migration
  def change
    create_table :types do |t|
      t.string :name
      t.string :clickertype

      t.timestamps
    end
        add_foreign_key :clickertypes

  end
end
